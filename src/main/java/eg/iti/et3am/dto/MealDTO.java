/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.dto;

/**
 *
 * @author Wael M Elmahask
 */
public class MealDTO {

    private Integer mealId;
    private String mealName;
    private float mealValue;
    private String mealImage;

    public MealDTO() {
    }

    public MealDTO(Integer mealId, String mealName, float mealValue, String mealImage) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealValue = mealValue;
        this.mealImage = mealImage;
    }

    public Integer getMealId() {
        return mealId;
    }

    public void setMealId(Integer mealId) {
        this.mealId = mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public float getMealValue() {
        return mealValue;
    }

    public void setMealValue(float mealValue) {
        this.mealValue = mealValue;
    }

    public String getMealImage() {
        return mealImage;
    }

    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }

}
