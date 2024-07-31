package batch.batch.batch.controller;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchTestController {
	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	public BatchTestController(JobLauncher jobLauncher, JobRegistry jobRegistry) {
		this.jobLauncher = jobLauncher;
		this.jobRegistry = jobRegistry;
	}

	@GetMapping("/first")
	public String firstApi(@RequestParam("value") String value) throws Exception {

		JobParameters jobParameters = new JobParametersBuilder()
			.addString("date", value)
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob("emotionRankJob"), jobParameters);

		return "ok";
	}
}
