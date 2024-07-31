package batch.batch.batch.config.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class EmotionRankSchedule {
	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;



	@Scheduled(cron = "0 0 5 * * *", zone = "Asia/Seoul") // 한국 시간으로 매 오전 05:00에 실행
	public void runFirstJob() throws Exception {

		System.out.println("first schedule start");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String date = dateFormat.format(new Date());

		JobParameters jobParameters = new JobParametersBuilder()
			.addString("date", date)
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob("emotionRankJob"), jobParameters);
	}
}
