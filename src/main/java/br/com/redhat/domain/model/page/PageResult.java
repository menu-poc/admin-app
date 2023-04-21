package br.com.redhat.domain.model.page;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResult<T> {
    List<T> results;
    Integer page;
    Integer numberOfPages;
}
