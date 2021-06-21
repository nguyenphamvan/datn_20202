package com.nguyenpham.oganicshop.converter;

import java.io.IOException;

public interface GeneralConverter<E, Dreq, Dres> {
    Dres entityToDto(E e);
    E dtoToEntity(Dreq d) throws IOException;
}
