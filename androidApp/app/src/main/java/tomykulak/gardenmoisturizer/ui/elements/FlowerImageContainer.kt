package tomykulak.gardenmoisturizer.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import tomykulak.gardenmoisturizer.R
import tomykulak.gardenmoisturizer.model.moistureSensor.MoistureData

@Composable
fun FlowerImageContainer(
    moistureData: MoistureData?
){
    val imgHeight = 450.dp
    if(moistureData != null){
        if(moistureData.percentage >= 80){
            Image(
                painter = painterResource(id = R.mipmap.happy_sunflower_foreground), // Replace with your actual resource
                contentDescription = "Happy Sunflower",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imgHeight)
            )
        } else if(moistureData.percentage in 50..80){
            Image(
                painter = painterResource(id = R.mipmap.okay_sunflower_foreground), // Replace with your actual resource
                contentDescription = "Okay Sunflower",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imgHeight)
            )
        } else if (moistureData.percentage in 1..50){
            Image(
                painter = painterResource(id = R.mipmap.sad_sunflower_foreground), // Replace with your actual resource
                contentDescription = "Sad Sunflower",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imgHeight)
            )

        }
        Text(text = "Moisture Int: ${moistureData.percentage}%")
        if (moistureData.isValveOpen){
            Text(text = "Valve is open")
        } else {
            Text(text = "Valve is closed")
        }
    } else {
        Text("No data or image sadge")
    }
}