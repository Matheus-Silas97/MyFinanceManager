package com.matheussilas97.myfinancemanager.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.matheussilas97.myfinancemanager.R

class MoneyMask {

    open fun setMask(editText: EditText, context: Context) {
        editText.setSelection(editText.text!!.length)
        var isUpdating = false
        var mask = "###"
        val formattedString = StringBuilder()
        editText.addTextChangedListener(object : TextWatcher {

            var countText = 0
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            @SuppressLint("StringFormatInvalid")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isUpdating) {
                    isUpdating = false
                } else {
                    //Função para aumentar ou diminuir o tamanho quando passar de 0,00
                    val value = s!!.replace("\\D".toRegex(), "")
                    if (before == 0 && Integer.parseInt(value.replace("\\D".toRegex(), ""))
                            .toString().length > 3
                    ) {
                        mask += "#"
                    } else if ((before == 1 && Integer.parseInt(
                            value.replace(
                                "\\D".toRegex(),
                                ""
                            )
                        ).toString().length >= 3)
                    ) {
                        mask = mask.drop(1)
                    }
                    //Variavel para verificar a quantidade de numeros para adicionar de tras pra frente
                    countText =
                        Integer.parseInt(value.replace("\\D".toRegex(), "")).toString().length

                    var i = 0
                    var lengthString = mask.length
                    //Guardando o valor que a pessoa digitou sem os zeros iniciais
                    val textWithoutMask =
                        Integer.parseInt(value.replace("\\D".toRegex(), "").toString()).toString()
                    //Apagando valor dentro da variavel, para poder repopular ela
                    formattedString.delete(0, formattedString.length)
                    //Laço for para fazer a população das informações
                    for (m in mask.toCharArray()) {
                        when {
                            lengthString <= countText -> {
                                formattedString.append(textWithoutMask[i])
                                i++
                            }
                            else -> {
                                formattedString.append("0")
                            }
                        }
                        lengthString--
                    }
                    isUpdating = true

                    //Condição para adicionar os pontos e virgulas quando o tamanho for maior ou menos que 6
                    if (mask.length >= 6) {
                        formattedString.insert(mask.length - 5, ".")
                        formattedString.insert(mask.length - 1, ",")
                    } else {
                        formattedString.insert(mask.length - 2, ",")
                    }

                    editText.setText(context.getString(R.string.cash, formattedString))
                    editText.setSelection(context.getString(R.string.cash, formattedString).length)
                }
            }
        })

    }
}