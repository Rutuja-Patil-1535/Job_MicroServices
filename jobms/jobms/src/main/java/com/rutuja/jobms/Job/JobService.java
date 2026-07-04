package com.rutuja.jobms.Job;

import com.rutuja.jobms.Job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);
    JobDTO getById(Long id);
    Boolean deleteById(Long id);
    Boolean updateJob(Long id,Job job);
}
