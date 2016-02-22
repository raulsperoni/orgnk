package mgcoders.uy.controller;

import mgcoders.uy.model.Departamento;
import mgcoders.uy.model.Frecuencia;
import mgcoders.uy.model.Localidad;
import mgcoders.uy.service.AuxService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by raul on 17/02/16.
 */
@Named
@ApplicationScoped
public class AuxController {

    @Inject
    AuxService auxService;

    @Inject
    private Logger log;

    private List<Departamento> departamentoList;
    private Map<Integer, List<Localidad>> localidadMap = new HashMap<>();
    private Map<Integer, Departamento> departamentoMap = new HashMap<>();
    private Map<Integer, Localidad> localidadIndividualMap = new HashMap<>();
    private List<Integer> montos = new ArrayList<>(Arrays.asList(10, 20, 50, 100, 200, 500));

    @PostConstruct
    private void initialize() {
        departamentoList = auxService.getDepartamentos();
        for (Departamento depto : departamentoList) {
            List<Localidad> localidades = auxService.getLocalidades(depto);
            for (Localidad localidad : localidades) {
                localidadIndividualMap.put(localidad.getId(), localidad);
            }
            localidadMap.put(depto.getId(), localidades);
            departamentoMap.put(depto.getId(), depto);
        }

    }


    @Named
    @Produces
    public List<Integer> getMontos() {
        return montos;
    }


    @Named
    @Produces
    public List<Departamento> getDepartamentoList() {
        return departamentoList;
    }

    @Named
    @Produces
    public List<Frecuencia> getFrecuenciaList() {
        return new ArrayList<>(Arrays.asList(Frecuencia.values()));
    }

    public Departamento getDepartamento(int idDepartamento) {
        return departamentoMap.get(idDepartamento);
    }

    public Localidad getLocalidad(int idLocalidad) {
        return localidadIndividualMap.get(idLocalidad);
    }

    public List<Localidad> getLocalidades(int idDepartamento) {
        return localidadMap.get(idDepartamento);
    }
}
