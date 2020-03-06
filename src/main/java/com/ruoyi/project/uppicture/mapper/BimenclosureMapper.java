package com.ruoyi.project.uppicture.mapper;

import com.ruoyi.project.uppicture.domain.Bimenclosure;

import java.util.List;

public interface BimenclosureMapper {
    public int insertBim(Bimenclosure bim);
    public List<Bimenclosure> SelectList(Bimenclosure bim);
    public Bimenclosure getBimbyId(int rowId);
    public Bimenclosure selectBim(Bimenclosure bim);

}
