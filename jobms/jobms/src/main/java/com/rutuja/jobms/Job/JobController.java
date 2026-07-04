package com.rutuja.jobms.Job;

import com.rutuja.jobms.Job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class JobController {
    private JobService jobService;
    public JobController(JobService jobService){
        this.jobService=jobService;
    }
    @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }
    @PostMapping("/jobs")
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return  new ResponseEntity<>("Job added successfully",HttpStatus.OK);
    }
    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobDTO j=jobService.getById(id);
        if(j!=null){
            return new ResponseEntity<>(j,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        Boolean j=jobService.deleteById(id);
        if(j){
            return new ResponseEntity<>("Deleted successfully!!!",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/jobs/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id,@RequestBody Job job){
        boolean updated=jobService.updateJob(id,job);
        if(updated){
            return new ResponseEntity<>("Updated SuccessFully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
