package entity;


import lombok.Data;


import java.io.Serializable;

/**
 * (Payment)实体类
 *
 * @author makejava
 * @since 2020-03-10 15:26:05
 */
@Data

public class Payment implements Serializable {
    
    private Long id;
    
    private String serial;


}