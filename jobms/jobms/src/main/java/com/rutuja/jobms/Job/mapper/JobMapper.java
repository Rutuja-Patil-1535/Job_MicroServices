package com.rutuja.jobms.Job.mapper;

import com.rutuja.jobms.Job.Job;
import com.rutuja.jobms.Job.dto.JobDTO;
import com.rutuja.jobms.Job.external.Company;
import com.rutuja.jobms.Job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews){
        JobDTO jobDTO =new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);
        return jobDTO;
    }
}
