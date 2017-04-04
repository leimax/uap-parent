package com.deppon.uap.message.model.po;

/**
 * Created by chenshengsheng on 2017/4/4.
 */
public class PerMesCheckbox {
    private String boxLabel;
    private String name;
    private String inputValue;
    private boolean checked = false ;
    private String id;

    @Override
    public String toString() {
        return "PerMesCheckbox{" +
                "boxLabel='" + boxLabel + '\'' +
                ", name='" + name + '\'' +
                ", inputValue='" + inputValue + '\'' +
                ", checked=" + checked +
                ", id='" + id + '\'' +
                '}';
    }

    public String getBoxLabel() {
        return boxLabel;
    }

    public void setBoxLabel(String boxLabel) {
        if (null==boxLabel&&"".equals(boxLabel))
        {
            boxLabel="css";
        }
        this.boxLabel = boxLabel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (null==name&&"".equals(name))
        {
            name="css";
        }
        this.name = name;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        if (null==inputValue&&"".equals(inputValue))
        {
            inputValue="css";
        }
        this.inputValue = inputValue;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (null==id&&"".equals(id))
        {
            id="css";
        }
        this.id = id;
    }
}
