/**
 * 
 */
package org.haijun.study.flow;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 对账服务接口实现
 * 2013-10-7上午10:27:37
 */
public class CreditServiceImpl implements CreditService {

	ResourceLoader loader = new DefaultResourceLoader();

	/**
	 * 实现解压缩文件
	 * @param inputFile
	 * @param outputDirectory
	 */
	@Override
	public void decompress(String inputFile, String outputDirectory) {
		Resource inputFileResource = loader.getResource(inputFile);
		Resource outputDirectoryResource = loader.getResource(outputDirectory);
		try {
			ZipUtils.decompressZip(inputFileResource.getFile().getAbsolutePath(), 
					outputDirectoryResource.getFile().getAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 校验文件处理实现
	 * @param outputDirectory
	 * @param readFileName
	 * @return
	 */
	@Override
	public String verify(String outputDirectory, String readFileName) {
		try {
			Resource outputDirectoryResource = loader.getResource(outputDirectory + readFileName);
			if(!outputDirectoryResource.exists()){
				return "FAILED";
			}
			if(!outputDirectoryResource.getFile().canRead()){
				return "SKIPTOCLEAN";
			}else {
				return "COMPLETED";
			}
		} catch (IOException e) {
			return "FAILED";
		}
	}

	/**
	 * 清空临时作业空间实现
	 * @param outputDirectory
	 */
	@Override
	public void clean(String outputDirectory) {
		Resource outputDirectoryResource = loader.getResource(outputDirectory);
		try {
			FileUtils.cleanDirectory(outputDirectoryResource.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查文件是否存在具体实现
	 * @param file
	 * @return
	 */
	@Override
	public boolean exists(String file) {
		Resource fileResource = loader.getResource(file);
		return fileResource.exists();
	}

}
