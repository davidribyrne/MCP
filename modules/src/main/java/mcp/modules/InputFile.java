package mcp.modules;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.commons.WorkingDirectories;
import space.dcce.commons.general.CryptoUtils;
import space.dcce.commons.general.FileUtils;


public class InputFile
{
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(InputFile.class);

	public InputFile(String filename, String directory)
	{
		this.filename = filename;
		this.directory = directory;
	}

	String filename;
	String directory;
	transient File file;
	long lastModified;
	byte[] hash;
	long size;

	synchronized File getFile()
	{
		if (file == null)
		{
			String path = WorkingDirectories.getInputFileDirectory() + File.separator + directory + File.separator + filename;
			file = new File(path);
		}
		return file;
	}

	void updateHash() throws IOException
	{
		byte[] content = FileUtils.readFileToByteArray(getFile());
		hash = CryptoUtils.sha1(content);
	}
	
	/**
	 * Has the file been changed since the last time it was checked
	 * @param rehash
	 * @return
	 * @throws IOException
	 */
	public boolean isModified(boolean rehash) throws IOException
	{
		long newModTime = getFile().lastModified();
		if (newModTime != lastModified)
		{
			rehash = true;
			lastModified = newModTime;
		}
		long newSize = getFile().length();
		if (size != newSize)
		{
			rehash = true;
			size = newSize;
		}
		if (rehash)
		{
			byte[] oldHash = hash;
			updateHash();
			return Arrays.equals(oldHash, hash);
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("directory", directory).append("filename", filename)
				.append("lastModified", lastModified).append("hash", hash).append("size", size)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(filename).append(directory)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		InputFile f = (InputFile) obj;
		return new EqualsBuilder().append(this.filename, f.filename).append(this.directory, f.directory)
				.isEquals();
	}

}
