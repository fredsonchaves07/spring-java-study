package com.fredsonchaves.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fredsonchaves.algafood.api.model.mixin.RestauranteMixin;
import com.fredsonchaves.algafood.domain.entity.Restaurante;

public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }
}
