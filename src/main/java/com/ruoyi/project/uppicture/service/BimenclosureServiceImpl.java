package com.ruoyi.project.uppicture.service;

import com.ruoyi.project.uppicture.domain.Bimenclosure;
import com.ruoyi.project.uppicture.mapper.BimenclosureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BimenclosureServiceImpl implements IBimenclosureService{


    @Autowired
    private BimenclosureMapper bimenclosureMapper;

    @Override
    public int insertBim(Bimenclosure bim) {
        return bimenclosureMapper.insertBim(bim);
    }

    @Override
    public List<Bimenclosure> SelectList(Bimenclosure bim) {
        return bimenclosureMapper.SelectList(bim);
    }

    @Override
    public Bimenclosure getBimbyId(int rowId) {
        return bimenclosureMapper.getBimbyId(rowId);
    }

    @Override
    public Bimenclosure selectBim(Bimenclosure bim) {
        return bimenclosureMapper.selectBim(bim);
    }
}
