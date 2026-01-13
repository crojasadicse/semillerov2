package com.semillero.semillero.commons;

import org.springframework.data.domain.PageImpl;

public interface IPaginationCommons<T> {

    public PageImpl<T> getPagination(PaginationModel paginationModel);

}
