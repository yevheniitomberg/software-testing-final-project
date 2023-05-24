package fun.tomberg.swedbankhm.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Currency implements Comparable<Currency>{
    private String code;
    private String country;
    private BigDecimal rate;

    @Override
    public int compareTo(Currency o) {
        return getCode().compareTo(o.getCode());
    }

    public Currency(String code, String country) {
        this.code = code;
        this.country = country;
    }
}
