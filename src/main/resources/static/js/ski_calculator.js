"use strict";
let heightUnit;
let skiLevel;
const FEET ="Feet";
const CENTIMETERS = "Centimeters";
const BEGINNERS = "Beginners";
const INTERMEDIATE = "Intermediate";
const ADVANCE = "Advance";
const EXPERT = "Expert";
const MIN_MEASURE_IN_FEET = 5;
const MID_MEASURE_IN_FEET = 5.5;
const MAX_MEASURE_IN_FEET = 6.2;
const MIN_MEASURE_IN_CM = 152.4;
const MID_MEASURE_IN_CM = 167.64;
const MAX_MEASURE_IN_CM = 189;
function getUnit(){
    heightUnit = document.getElementById("heightUnit").value;
}

function validateHeight(height){
    if (height=="") {
        alert("Please Enter Your Height");
        document.getElementById("height").focus();
        return false;
    } else if(parseFloat(height)<0){
        alert("Invalid Height");
        document.getElementById("height").focus();
        return false;
    }{ return true; }
}

function getSkiLevel(){
    let radioButtons = document.getElementsByName("skiLevel");
    for (let i = 0; i < radioButtons.length; i++){
        if(radioButtons[i].type=="radio" && radioButtons[i].checked==true){
            return radioButtons[i].value;
        }
    }
    return ""; 
}

function calculateSkiSize(){
    let height = document.getElementById("height").value;
    skiLevel = getSkiLevel();
    if (skiLevel=="") {
        alert("Please choose your ski level");
        document.getElementById("skiLevelForm").focus();
        return false;
    }
    if (!validateHeight(height)){
        return false;
    }
    if (heightUnit!=FEET && heightUnit!="Centimeters"){
        alert("Please choose the unit: feet or cm");
        document.getElementById("heightUnit").focus();
        return false;
    }
    generateSizingResult(height);
    return true;
}

function generateSizingResult(height){
    if (((heightUnit==FEET) && (parseFloat(height) < MIN_MEASURE_IN_FEET || parseFloat(height) > MAX_MEASURE_IN_FEET)) ||
        ((heightUnit==CENTIMETERS) && (parseFloat(height) < MIN_MEASURE_IN_CM || parseFloat(height) > MAX_MEASURE_IN_CM))){
            document.getElementById("skiSizeSuggestion").innerHTML = "Sorry, out of range";
    } else if ((heightUnit==FEET && parseFloat(height) >= MIN_MEASURE_IN_FEET && parseFloat(height) <= MID_MEASURE_IN_FEET)||
        (heightUnit==CENTIMETERS && parseFloat(height) >= MIN_MEASURE_IN_CM && parseFloat(height) <= MID_MEASURE_IN_CM)){
            if (skiLevel==BEGINNERS){
                document.getElementById("skiSizeSuggestion").innerHTML = "Suggested Ski Length: 155cm";
            } else if (skiLevel== INTERMEDIATE ){
                document.getElementById("skiSizeSuggestion").innerHTML = "Suggested Ski Length: 160cm";
            } else if(skiLevel==ADVANCE){
                document.getElementById("skiSizeSuggestion").innerHTML = "Suggested Ski Length: 165cm";
            } else if(skiLevel==EXPERT){
                document.getElementById("skiSizeSuggestion").innerHTML = "Suggested Ski Length: 170cm";
            }
    } else if ((heightUnit==FEET && parseFloat(height) > MID_MEASURE_IN_FEET && parseFloat(height) <= MAX_MEASURE_IN_FEET) ||
        (heightUnit==CENTIMETERS && parseFloat(height) > MID_MEASURE_IN_CM && parseFloat(height) <= MAX_MEASURE_IN_CM)){
            if (skiLevel==BEGINNERS){
                document.getElementById("skiSizeSuggestion").innerHTML = "Suggested Ski Length: 175cm";
            } else if (skiLevel==INTERMEDIATE ){
                document.getElementById("skiSizeSuggestion").innerHTML = "Suggested Ski Length: 180cm";
            } else if(skiLevel==ADVANCE){
                document.getElementById("skiSizeSuggestion").innerHTML = "Suggested Ski Length: 185cm";
            } else if(skiLevel==EXPERT){
                document.getElementById("skiSizeSuggestion").innerHTML = "Suggested Ski Length: 190cm";
            }
    }
}