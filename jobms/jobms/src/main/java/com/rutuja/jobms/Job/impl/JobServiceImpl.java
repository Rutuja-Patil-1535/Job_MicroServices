package com.rutuja.jobms.Job.impl;


import com.rutuja.jobms.Job.Job;
import com.rutuja.jobms.Job.JobRepository;
import com.rutuja.jobms.Job.JobService;
import com.rutuja.jobms.Job.clients.CompanyClient;
import com.rutuja.jobms.Job.clients.ReviewClient;
import com.rutuja.jobms.Job.dto.JobDTO;
import com.rutuja.jobms.Job.external.Company;
import com.rutuja.jobms.Job.external.Review;
import com.rutuja.jobms.Job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
   //private List<Job> jobs=new ArrayList<>();
    private JobRepository jobRepository;
    @Autowired
    RestTemplate restTemplate;
    private CompanyClient companyClient;
    private ReviewClient reviewClient;
    int attempt=0;

    public JobServiceImpl(JobRepository jobRepository,CompanyClient companyClient,ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient=companyClient;
        this.reviewClient=reviewClient;
    }

    //private Long nextId=1L;
    @Override
    //@CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    //@Retry(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt:"+ ++attempt);
        List<Job> jobs=jobRepository.findAll();
        //List<JobDTO> jobDTOS =new ArrayList<>();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
        /*here stream helps to process elements one by one and map helps to apply converttodo function for each element
        and the proceeds data is stored in the new list this work done by collect */
    }
    public List<String> companyBreakerFallback(Exception e){
        List<String> list=new ArrayList<>();
        list.add("Dummy");
        return list;
    }
    private JobDTO convertToDto(Job job){
        Company company=companyClient.getCompany(job.getCompanyId());
        List<Review> reviews =reviewClient.getReviews(job.getCompanyId());
        //Company company=restTemplate.getForObject("http://companyms:8082/companies/"+job.getCompanyId(), Company.class);
        /*ResponseEntity<List<Review>> reviewResponse=restTemplate.exchange("http://reviewms:8081/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
        });
        List<Review> reviews=reviewResponse.getBody();*/
        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job,company,reviews);
        //jobWithCompanyDTO.setCompany(company);
        return jobDTO;
    }
    @Override
    public void createJob(Job job) {
         jobRepository.save(job);

    }

    @Override
    public JobDTO getById(Long id) {
       Job job=  jobRepository.findById(id).orElse(null);
       return convertToDto(job);

    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateJob(Long id, Job job) {
        Optional<Job> jobOptional=jobRepository.findById(id);
            if(jobOptional.isPresent()){
                Job j=jobOptional.get();
                j.setDescription(job.getDescription());
                j.setLocation(job.getLocation());
                j.setTitle(job.getTitle());
                j.setMaxSalary(job.getMaxSalary());
                j.setMinSalary(job.getMinSalary());
                jobRepository.save(j);
                return true;
            }
             return false;
    }

}
