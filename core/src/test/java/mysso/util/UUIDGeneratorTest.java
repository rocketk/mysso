package mysso.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by pengyu.
 */
@RunWith(Parameterized.class)
public class UUIDGeneratorTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"tgt-", ".localhost", 10000}, // 前缀tgt-，后缀.localhost，循环10000次
                {"st-", ".localhost", 10000},
                {"tk-", ".localhost", 10000},
                {"tgt-", "", 10000},
                {"st-", "", 10000},
                {"tk-", "", 10000},
                {"", "", 10000}
        });
    }

    @Parameterized.Parameter
    public String prefix;
    @Parameterized.Parameter(1)
    public String suffix;
    @Parameterized.Parameter(2)
    public int n;

    @Test
    public void getNewId() throws Exception {
        UniqueIdGenerator generator = new UUIDGenerator(prefix, suffix);
        Set<String> idSet = new HashSet<>();
        // 循环n次，测试其不会产生重复的id
        for (int i = 0; i < n; i++) {
            idSet.add(generator.getNewId());
        }
        assertEquals(n, idSet.size());
    }

}