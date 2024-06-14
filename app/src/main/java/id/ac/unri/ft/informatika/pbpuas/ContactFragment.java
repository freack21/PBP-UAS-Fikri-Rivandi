package id.ac.unri.ft.informatika.pbpuas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        
        int[] emailFieldIds = {R.id.email1, R.id.email2, R.id.email3, R.id.email4, R.id.email5, R.id.email6, R.id.email7, R.id.email8, R.id.email9};
        int[] phoneFieldIds = {R.id.nomor1, R.id.nomor2, R.id.nomor3, R.id.nomor4, R.id.nomor5, R.id.nomor6, R.id.nomor7, R.id.nomor8, R.id.nomor9};
        int[] webFieldIds = {R.id.web1, R.id.web2, R.id.web3, R.id.web4, R.id.web5, R.id.web6, R.id.web7, R.id.web8, R.id.web9};

        for (int i = 0; i < emailFieldIds.length; i++) {
            LinearLayout emailField = rootView.findViewById(emailFieldIds[i]);
            emailField.setOnClickListener(listener(emailField, 0));
        }

        for (int i = 0; i < phoneFieldIds.length; i++) {
            LinearLayout phoneField = rootView.findViewById(phoneFieldIds[i]);
            phoneField.setOnClickListener(listener(phoneField, 1));
        }

        for (int i = 0; i < webFieldIds.length; i++) {
            LinearLayout webField = rootView.findViewById(webFieldIds[i]);
            webField.setOnClickListener(listener(webField, 2));
        }

        return rootView;
    }

    public View.OnClickListener listener(LinearLayout ll, int type) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView strTextView = (TextView) ll.getChildAt(0);
                String str = strTextView.getText().toString().trim();
                if (!str.isEmpty()) {
                    if (type == 0) {
                        openEmailIntent(str);
                    } else if (type == 1) {
                        openPhoneIntent(str);
                    } else if (type == 2) {
                        openWebsiteIntent(str);
                    }
                }
            }
        };
    }

    private void openEmailIntent(String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));
        startActivity(intent);
    }

    private void openPhoneIntent(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void openWebsiteIntent(String websiteUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://" + websiteUrl));
        startActivity(intent);
    }
}
