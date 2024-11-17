package tm.esprit.fonctionpariteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tm.esprit.fonctionpariteapp.ui.theme.FonctionPariteAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FonctionPariteAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ParityCheckerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ParityCheckerScreen(modifier: Modifier = Modifier) {
    // Variables d'état
    val functionInput = remember { mutableStateOf(TextFieldValue("")) }
    val result = remember { mutableStateOf("") }

    // Composants de l'interface
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Vérification de la parité de la fonction",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = functionInput.value,
            onValueChange = { functionInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(8.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
            singleLine = false,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                result.value = checkParity(functionInput.value.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Vérifier la parité", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Résultat : ${result.value}",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

fun checkParity(function: String): String {
    // Remplace les espaces et normalise la fonction
    val cleanedFunction = function.replace(" ", "").replace("^", "**")

    // Divise la fonction en termes
    val terms = cleanedFunction.split("+", "-").map { it.trim() }
    var isEven = true
    var isOdd = true

    for (term in terms) {
        if (term.isNotEmpty()) {
            // Détermine la puissance de x
            val power = when {
                term.contains("x**") -> term.split("x**")[1].toInt()
                term.contains("x*") -> term.split("x*")[1].toInt()
                term.contains("x") -> 1
                else -> 0
            }

            if (power % 2 == 0) {
                isOdd = false
            } else {
                isEven = false
            }
        }
    }

    return when {
        isEven -> "Paire"
        isOdd -> "Impaire"
        else -> "Ni paire ni impaire"
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FonctionPariteAppTheme {
        ParityCheckerScreen()
    }
}
