/**
 * 
 */
package org.haijun.study.flow.tasklet;

import org.haijun.study.flow.CreditService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * 解压缩任务
 * 2013-10-7上午10:18:34
 */
public class DecompressTasklet implements Tasklet {
	private CreditService creditService; // 对账单业务接口
	private String inputFile;// 压缩数据包
	private String outputDirectory;// 数据解压到的工作目录

	/**
	 * 执行操作
	 * @param contribution
	 * @param chunkContext
	 * @return
	 * @throws Exception
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		creditService.decompress(inputFile, outputDirectory);
		return RepeatStatus.FINISHED;
	}
	// 设定对账业务具体实现
	public void setCreditService(CreditService creditService) {
		this.creditService = creditService;
	}
	// 设定数据的压缩文件
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	// 设定解压的工作目录
	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}
}
