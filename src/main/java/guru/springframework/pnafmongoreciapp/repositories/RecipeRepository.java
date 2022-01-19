package guru.springframework.pnafmongoreciapp.repositories;

import guru.springframework.pnafmongoreciapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
