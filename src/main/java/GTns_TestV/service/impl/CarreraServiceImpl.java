package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.CarreraRepository;
import GTns_TestV.model.entity.Carrera;

import GTns_TestV.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarreraServiceImpl implements CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

    @Override
    public List<Carrera> listarCarreras() {
        return carreraRepository.findAll();
    }
}
