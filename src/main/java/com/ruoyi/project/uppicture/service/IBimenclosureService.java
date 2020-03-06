package com.ruoyi.project.uppicture.service;

import com.ruoyi.project.uppicture.domain.Bimenclosure;

import java.util.List;

public interface IBimenclosureService {
    public int insertBim(Bimenclosure bim);
    public List<Bimenclosure> SelectList(Bimenclosure bim);
    public Bimenclosure getBimbyId(int rowId);
    public Bimenclosure selectBim(Bimenclosure bim);
}
