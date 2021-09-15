package wang.lonelymoon.scaffold.service;

import java.util.List;

/**
 * 增、删、改、查、导入、导出
 */
public interface BaseCrudService<Dto,Vo> {
    Integer Insert(Dto dto);

    Integer Delete(Dto dto);

    Integer Update(Dto dto);

    List<Vo> List(Dto dto);

    List<Vo> ListByPage(Dto dto);

    void Import(Dto dto);

    void Export(Dto dto);
}
