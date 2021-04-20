package com.nguyenpham.oganicshop.converter;

public interface GeneralConverter<E, Dreq, Dres> {

    Dres entityToDto(E e);

    E dtoToEntity(Dreq d);



}
