/**
 *
 */
package io.yope.careers.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Massimiliano Gerardi
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Page<T> {

    private List<T> elements;

    private Long total;

    private Integer pages;

    private Integer from;

    private Boolean last;

}
