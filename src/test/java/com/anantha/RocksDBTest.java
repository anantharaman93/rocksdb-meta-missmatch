package com.anantha;

import org.junit.jupiter.api.Test;
import org.rocksdb.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class RocksDBTest
{
	@Test
	public void testRocksDB() throws RocksDBException
	{
		String dbPath = System.getProperty("rocksdb_dir");
		
		List<byte[]> columnFamilies;
		try (Options o = new Options())
		{
			columnFamilies = RocksDB.listColumnFamilies(o, dbPath);
		}
		
		List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
		DBOptions dbOptions = new DBOptions().setCreateIfMissing(true).setCreateMissingColumnFamilies(true);
		try (ConfigOptions configOptions = new ConfigOptions().setIgnoreUnknownOptions(false).setInputStringsEscaped(true).setEnv(Env.getDefault()))
		{
			OptionsUtil.loadLatestOptions(configOptions, dbPath, dbOptions, columnFamilyDescriptors);
		}
		
		List<String> columnFamilyNames = columnFamilies.stream().map(String::new).collect(Collectors.toList());
		List<String> columnDescriptorNames = columnFamilyDescriptors.stream().map(ColumnFamilyDescriptor::getName).map(String::new).collect(Collectors.toList());
		
		assertThat(columnFamilyNames).containsExactlyInAnyOrderElementsOf(columnDescriptorNames);
	}
}
