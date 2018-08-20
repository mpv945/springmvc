package org.haijun.study.util;

import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;

import javax.annotation.Resource;

/**
 *  繼承FlatXmlDataSetLoader的自訂資料集合載入器MyDataSetLoader
 */
public class MyDataSetLoader extends FlatXmlDataSetLoader {
    /**
     * 2. 覆寫方法createDataSet，這邊是當取得的DataSet中遇到字串"[empty_string]"時，將其轉為空字串""
     */
    @Override
    protected IDataSet createDataSet(org.springframework.core.io.Resource resource) throws Exception {
        IDataSet flatXmlDataSet = super.createDataSet(resource);
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(flatXmlDataSet);
        replacementDataSet.addReplacementObject("[empty_string]", "");
        return replacementDataSet;
    }
}
