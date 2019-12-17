package com.github.admin.server.constant;

/**
 * @Description: 通用状态
 */
public enum CommonState {
    /**
     * 枚举列表
     */
    INVALID(0, "无效"), VALID(1, "有效");
    /**
     * 对应数值
     */
    private final Integer val;
    /**
     * 显示值
     */
    private final String label;

    /**
     * 构造函数
     * @param val   枚举数值
     * @param label 显示值
     */
    CommonState(Integer val, String label) {
        this.val = val;
        this.label = label;
    }

    /**
     * 按照枚举数值找到对应枚举
     * @param val 枚举数值
     * @return 对应的枚举, 可能为null.
     */
    public static CommonState valueOf(Integer val) {
        CommonState[] ems = CommonState.values();
        for (CommonState em : ems) {
            if (em.val.equals(val)) {
                return em;
            }
        }
        return null;
    }

    /**
     * @return the val
     */
    public Integer getVal() {
        return val;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 通过枚举值获取对应label
     * @param val 枚举值
     * @return label
     */
    public static String labelOf(Integer val) {
        CommonState em = valueOf(val);
        if (em == null) {
            return null;
        }
        return em.getLabel();
    }
}
