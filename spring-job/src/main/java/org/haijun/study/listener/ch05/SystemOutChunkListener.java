/**
 * 
 */
package org.haijun.study.listener.ch05;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * 默认实现： ChunkListenerSupport（默认实现）和CompositeChunkListener 组合
 * 2012-9-1上午08:07:17
 */
public class SystemOutChunkListener implements ChunkListener {

//	/* (non-Javadoc)
//	 * @see org.springframework.batch.core.ChunkListener#beforeChunk()
//	 */
//	public void beforeChunk() {
//		System.out.println("ChunkListener.beforeChunk()");
//	}
//
//	/* (non-Javadoc)
//	 * @see org.springframework.batch.core.ChunkListener#afterChunk()
//	 */
//	public void afterChunk() {
//		System.out.println("ChunkListener.afterChunk()");
//	}

	// Chunk 执行前（注解@BeforeChunk）
	@Override
	public void beforeChunk(ChunkContext context) {
		System.out.println("ChunkListener.beforeChunk()");
	}

	// Chunk 执行后
	@Override
	public void afterChunk(ChunkContext context) {
		System.out.println("ChunkListener.afterChunk()");
	}

	// Chunk 发生异常时
	@Override
	public void afterChunkError(ChunkContext context) {
		System.out.println("ChunkListener.afterChunkError()");
	}

}
