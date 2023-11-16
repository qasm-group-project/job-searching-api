package uk.ac.le.qasm.job.searching.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import uk.ac.le.qasm.job.searching.api.entity.Provider;

@Mapper
public interface ProviderMapper extends BaseMapper<Provider> {

}
