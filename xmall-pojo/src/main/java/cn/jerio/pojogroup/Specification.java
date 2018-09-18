package cn.jerio.pojogroup;

import cn.jerio.pojo.TbSpecification;
import cn.jerio.pojo.TbSpecificationOption;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jerio on 2018/9/18
 */
@Data
public class Specification implements Serializable {

    private TbSpecification specification;

    private List<TbSpecificationOption> specificationOptionList;

}
