package org.haijun.study.handler;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.LineCallbackHandler;

/**
 * 
 * 实现从ItemReader 读取跳过的头信息，然后FlatFileHeaderCallback 的writeHeader写方法把头信息写过去
 * 2013-4-3下午08:01:45
 */
public class CopyHeaderLineCallbackHandler implements LineCallbackHandler,
		FlatFileHeaderCallback {
	private String header = "";

	public void handleLine(String line) {
		this.header = line;
	}

	public void writeHeader(Writer writer) throws IOException {
		writer.write(header);
	}
}
