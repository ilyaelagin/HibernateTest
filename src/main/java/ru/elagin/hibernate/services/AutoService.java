package ru.elagin.hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.elagin.hibernate.dto.AutoFilter;
import ru.elagin.hibernate.models.Auto;
import ru.elagin.hibernate.repository.AutoRepository;
import ru.elagin.hibernate.response.Response;

@Service
public class AutoService {

    private final AutoRepository autoRepository;

    @Autowired
    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public Response index(AutoFilter autoFilter) {
        Response response = new Response();
        try {
            response.setObjects(autoRepository.index(autoFilter));
        } catch (Exception e) {
            response.setError("Error occurred during the show all autos");
        }
        return response;
    }

    public Response show(Integer id) {
        Response response = new Response();
        try {
            response.setObject(autoRepository.show(id));
        } catch (Exception e) {
            response.setError("Error occurred during show data");
        }
        return response;
    }

    public Response save(Auto auto) {
        try {
            if (auto.getCustomer().getId() == null)
                auto.setCustomer(null);
            autoRepository.save(auto);
            return new Response();
        } catch (Exception e) {
            Response response = new Response();
            response.setError("Error occurred during save data");
            return response;
        }
    }

    public Response update(Integer id, Auto auto) {
        try {
            if (auto.getCustomer().getId() == null)
                auto.setCustomer(null);
            autoRepository.update(id, auto);
            return new Response();
        } catch (Exception e) {
            Response response = new Response();
            response.setError("Error occurred during update data");
            return response;
        }
    }

    public Response delete(Integer id) {
        try {
            autoRepository.delete(id);
            return new Response();
        } catch (Exception e) {
            Response response = new Response();
            response.setError("Error occurred delete save data");
            return response;
        }
    }
}
