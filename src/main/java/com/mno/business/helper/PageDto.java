package com.mno.business.helper;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PageDto {

    private int number,page_size;

}
