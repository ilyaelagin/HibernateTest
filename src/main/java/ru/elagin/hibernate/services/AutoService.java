package ru.elagin.hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.elagin.hibernate.dto.AutoFilter;
import ru.elagin.hibernate.models.Auto;
import ru.elagin.hibernate.repository.AutoRepository;
import ru.elagin.hibernate.response.AutoResponse;

@Service
public class AutoService {

    private final AutoRepository autoRepository;

    @Autowired
    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public AutoResponse index(AutoFilter autoFilter) {
        AutoResponse response = new AutoResponse();
        try {
            response.setAutoList(autoRepository.index(autoFilter));
        } catch (Exception e) {
            response.setError("Error occurred during the show all autos");
        }
        return response;
    }

    public AutoResponse show(Integer id) {
        AutoResponse response = new AutoResponse();
        try {
            response.setAuto(autoRepository.show(id));
        } catch (Exception e) {
            response.setError("Error occurred during show data");
        }
        return response;
    }

    public AutoResponse save(Auto auto) {
        try {
            if (auto.getCustomer().getId() == null)
                auto.setCustomer(null);
            autoRepository.save(auto);
            return new AutoResponse();
        } catch (Exception e) {
            AutoResponse response = new AutoResponse();
            response.setError("Error occurred during save data");
            return response;
        }
    }

    public AutoResponse update(Integer id, Auto auto) {
        try {
            if (auto.getCustomer().getId() == null)
                auto.setCustomer(null);
            autoRepository.update(id, auto);
            return new AutoResponse();
        } catch (Exception e) {
            AutoResponse response = new AutoResponse();
            response.setError("Error occurred update save data");
            return response;
        }
    }

    public AutoResponse delete(Integer id) {
        try {
            autoRepository.delete(id);
            return new AutoResponse();
        } catch (Exception e) {
            AutoResponse response = new AutoResponse();
            response.setError("Error occurred delete save data");
            return response;
        }
    }
}
