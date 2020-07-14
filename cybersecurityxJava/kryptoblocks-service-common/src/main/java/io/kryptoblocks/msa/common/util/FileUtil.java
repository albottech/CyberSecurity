package io.kryptoblocks.msa.common.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new file util.
 */
@Data
public class FileUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * Write to file with lock.
	 *
	 * @param fileName the file name
	 * @param data the data
	 */
	public void writeToFileWithLock(String fileName, String data) {

		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
			FileChannel fc = randomAccessFile.getChannel();
			ByteBuffer buffer = null;

			FileLock fileLock = fc.tryLock();
			if (null != fileLock) {
				buffer = ByteBuffer.wrap(data.getBytes());
				buffer.put(data.toString().getBytes());
				buffer.flip();
				while (buffer.hasRemaining())
					fc.write(buffer);
				
			}
			randomAccessFile.close();
		} catch (OverlappingFileLockException | IOException ex) {
			LOGGER.error("Exception occured while trying to get a lock on File... " + ex.getMessage());
		}
	}
}