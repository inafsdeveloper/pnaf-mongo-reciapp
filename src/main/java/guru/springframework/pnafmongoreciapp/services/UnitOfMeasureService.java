package guru.springframework.pnafmongoreciapp.services;


import guru.springframework.pnafmongoreciapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    public Set<UnitOfMeasureCommand> listAllUoms();
}
