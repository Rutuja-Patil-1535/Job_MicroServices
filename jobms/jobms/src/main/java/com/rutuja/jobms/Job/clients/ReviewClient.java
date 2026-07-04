package com.rutuja.jobms.Job.clients;

import com.rutuja.jobms.Job.external.Company;
import com.rutuja.jobms.Job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="reviewms",url="${reviewms.url}")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getReviews(@RequestParam("companyId") Long id);

}
