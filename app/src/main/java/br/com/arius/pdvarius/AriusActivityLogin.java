package br.com.arius.pdvarius;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import arius.pdv.base.PdvService;
import arius.pdv.base.UsuarioDao;
import arius.pdv.core.AppContext;
import arius.pdv.db.AndroidUtils;
import arius.pdv.db.AriusAlertDialog;

/**
 * Created by Arius on 25/10/2017.
 */

public class AriusActivityLogin extends ActivityPadrao {

    private Context context;
    private Button btnLogin;
    AriusActivityPercValor ariusActivityPercValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        montaLogin(null, getApplicationContext());

    }

    public void montaLogin(View view, final Context context){
        this.context = context;

        ariusActivityPercValor = new AriusActivityPercValor();

        if (view == null){
            btnLogin = (Button) findViewById(R.id.btn_Entrar);
        } else {
            btnLogin = view.findViewById(R.id.btn_Entrar);
        }

        if (btnLogin != null)
            setButtonLogin();

        Button btn = view.findViewById(R.id.btnProgressbar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.setProgressBar(context);
            }
        });
    }

    private void setButtonLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AriusAlertDialog.exibirDialog(context,R.layout.contentariusdialogpercvalor);
                ariusActivityPercValor.montaDialog_Campos(AriusAlertDialog.getAlertDialog(), v);
                //ariusActivityPercValor.setTitulo("Valor Inicial");
                ariusActivityPercValor.setUtilizaPorcentagem(false);

                AriusAlertDialog.getGetView().findViewById(R.id.btnContentDialogValorCancelar).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AriusAlertDialog.getAlertDialog().dismiss();
                            }
                        }
                );

                AriusAlertDialog.getGetView().findViewById(R.id.btnContentDialogValorConfirmar).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (ariusActivityPercValor.getRetorno_valor() == 0)
                                    AndroidUtils.toast(context,"Informar um valor para a abertura do caixa!");
                                else {
                                    PdvService.get().setOperadorAtual(AppContext.get().getDao(UsuarioDao.class).find(1));
                                    PdvService.get().abreCaixa(
                                            PdvService.get().getOperadorAtual(),
                                            ariusActivityPercValor.getRetorno_valor()
                                    );

                                    AriusAlertDialog.getAlertDialog().dismiss();

                                    ((AriusActivityPrincipal) context).onStart();
                                }
                            }
                        }
                );

            }
        });
    }
}