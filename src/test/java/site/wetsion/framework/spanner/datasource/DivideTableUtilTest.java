package site.wetsion.framework.spanner.datasource;

import org.junit.Assert;
import org.junit.Test;

public class DivideTableUtilTest {

    @Test
    public void testGetTableName() {
        Assert.assertEquals(DivideTableUtil.getTableName("user", 1, 128), "user_1");
    }

    @Test
    public void testGetTableNameV2() {
        Assert.assertEquals(DivideTableUtil.getTableNameV2("user", 129, 128), "user_1");
    }
}
