package uk.ac.le.qasm.job.searching.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
@Mapper
public interface ProviderMapper extends MPJBaseMapper<Provider> {
}
