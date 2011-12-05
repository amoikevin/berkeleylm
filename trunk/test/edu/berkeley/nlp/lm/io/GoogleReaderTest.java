package edu.berkeley.nlp.lm.io;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.berkeley.nlp.lm.ArrayEncodedNgramLanguageModel;
import edu.berkeley.nlp.lm.StupidBackoffLm;
import edu.berkeley.nlp.lm.cache.ArrayEncodedCachingLmWrapper;

public class GoogleReaderTest
{
	@Test
	public void testHash() {
		final ArrayEncodedNgramLanguageModel<String> lm = LmReaders.readLmFromGoogleNgramDir(FileUtils.getFile("googledir").getPath(), false, false);
		checkScores(lm);
	}

	@Test
	public void testHashCached() {
		final ArrayEncodedNgramLanguageModel<String> lm = LmReaders.readLmFromGoogleNgramDir(FileUtils.getFile("googledir").getPath(), false, false);
		checkScores(ArrayEncodedCachingLmWrapper.wrapWithCacheNotThreadSafe(lm));
	}

	@Test
	public void testCompressed() {
		final ArrayEncodedNgramLanguageModel<String> lm = LmReaders.readLmFromGoogleNgramDir(FileUtils.getFile("googledir").getPath(), true, false);
		checkScores(lm);
	}

	/**
	 * @param lm
	 */
	private void checkScores(final ArrayEncodedNgramLanguageModel<String> lm) {
		Assert.assertEquals(lm.getLogProb(Arrays.asList("the", "(")), -12.314105, 1e-3);
		Assert.assertEquals(lm.getLogProb(Arrays.asList("of", "the", "(")), -6.684612, 1e-3);
		Assert.assertEquals(lm.getLogProb(Arrays.asList("of", "the", "(")), -6.684612, 1e-3);
		Assert.assertEquals(lm.getLogProb(Arrays.asList("a", "the", "(")), -13.230395, 1e-3);
		Assert.assertEquals(lm.getLogProb(Arrays.asList("a", ")", "(")), -5.6564045, 1e-3);
		Assert.assertEquals(lm.getLogProb(Arrays.asList("the", "of", "a")), -15.491532, 1e-3);
		Assert.assertEquals(lm.getLogProb(Arrays.asList("of", "the", "(")), -6.684612, 1e-3);
	}

}
