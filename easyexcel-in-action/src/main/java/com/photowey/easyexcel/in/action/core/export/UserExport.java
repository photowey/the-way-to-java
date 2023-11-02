/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.easyexcel.in.action.core.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.photowey.common.in.action.date.DatePatternConstants;
import com.photowey.common.in.action.number.NumberConstants;
import com.photowey.easyexcel.in.action.converter.GenderIntegerWrite2StringConverter;
import com.photowey.easyexcel.in.action.converter.IntegerWrite2StringConverter;
import com.photowey.easyexcel.in.action.converter.WriteLong2StringConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * {@code UserExport}
 *
 * @author photowey
 * @date 2023/03/19
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@HeadRowHeight(20)
@HeadFontStyle(fontHeightInPoints = 12)
public class UserExport implements Serializable {

    private static final long serialVersionUID = -3021644356652917349L;

    @ColumnWidth(20)
    @ExcelProperty(value = "标识", converter = WriteLong2StringConverter.class)
    private Long id;

    @ColumnWidth(20)
    @ExcelProperty("姓名")
    private String name;

    @ColumnWidth(5)
    @ExcelProperty(value = "年龄", converter = IntegerWrite2StringConverter.class)
    private Integer age;

    @ColumnWidth(10)
    @ExcelProperty(value = "性别", converter = GenderIntegerWrite2StringConverter.class)
    private Integer gender;

    @ColumnWidth(10)
    @ExcelProperty("余额")
    @NumberFormat(NumberConstants.TWO_DECIMAL_POINTS)
    private BigDecimal balance;

    @ColumnWidth(18)
    @ExcelProperty("加入时间")
    @DateTimeFormat(DatePatternConstants.yyyy_MM_dd_HH_mm_ss)
    private LocalDateTime joinTime;
}
