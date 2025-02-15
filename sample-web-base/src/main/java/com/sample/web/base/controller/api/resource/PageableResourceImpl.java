package com.sample.web.base.controller.api.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sample.domain.dto.common.Dto;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageableResourceImpl extends ResourceImpl implements PageableResource {

  int page = 1;

  int totalPages;

  public PageableResourceImpl() {}

  public PageableResourceImpl(List<? extends Dto> content, int page, int totalPages) {
    this.content = content;
    this.page = page;
    this.totalPages = totalPages;
  }
}
