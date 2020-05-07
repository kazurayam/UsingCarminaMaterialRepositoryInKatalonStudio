import java.nio.file.Path

import com.kazurayam.visualtesting.GlobalVariableHelpers as GVH

import com.kazurayam.materials.MaterialRepository
import com.kazurayam.visualtesting.ImageDiffer
import com.kazurayam.visualtesting.ImageDiffsLister
import com.kazurayam.visualtesting.ManagedGlobalVariable as MGV
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

// Test Cases/CURA/ImageDiff_twins

/**
 * compare 2 sets of images and produce diff-images
 */
assert GVH.isGlobalVariablePresent(MGV.LAST_EXECUTED_TESTSUITE_ID)
String TESTSUITE_ID = GVH.getGlobalVariableValue(MGV.LAST_EXECUTED_TESTSUITE_ID) // e.g, 'CURA/twins_capture'

double criteriaPercentage = 1.0
ImageDiffer imageDiffer = new ImageDiffer()
boolean result = imageDiffer.runTwins(TESTSUITE_ID, criteriaPercentage)

if (! result ) {
	KeywordUtil.markFailed("One or more pairs of screenshot are different.")
}
