package com.surakshasathi;

import static android.text.Html.fromHtml;

import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ChildRight extends Fragment {
    TextView content;
    public ChildRight() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=  inflater.inflate( R.layout.fragment_child_right, container, false );

       content = view.findViewById( R.id.childcontent );

        String cont = "1. <b>ARTICLE 15:</b> Bacchon ke liye \"<i>No Discrimination</i>\" ka magic wand, jisse har kisi ko fair treatment mile, bina kisi bhed-bhav ke, aur bachpan ho masti bhara! A ward of equity to Reject Discrimination.<br><br>" +
                "2. <b>ARTICLE 21:</b> \"<i>Life ka VIP Pass</i>\" bacchon ko, jisme hai suraksha aur gender-based violence se mukti, ensuring ki har baccha jeeyen, khush rhain aur surakshit bachpan jeeyen.<br><br>" +
                "3. <b>ARTICLE 42:</b> Ladkiyon ke liye \"<i>Special Shield</i>,\" secure working conditions aur empowerment ke liye, ensuring ki unki unchayiyon ko koi rok na sake.<br><br>" +
                "4. <b>POCSO ACT:</b> Bacchon ke liye \"<i>Anti-Sexual Abuse Armor</i>,\" jo unhein sexual offenses se bachata hai, ek sashakt bachpan ke liye.<br><br>" +
                "5. <b>JUVENILE JUSTICE ACT:</b> Court ka \"<i>Protection Shield</i>\" bacchon ke liye, ensuring justice aur unki rehabilitation.<br><br>" +
                "6. <b>NATIONAL COMMISSION FOR PROTECTION OF CHILD RIGHTS (NCPCR):</b> Childhood ke \"<i>Guardians</i>,\" jo bacchon ke rights ko protect karte hain aur crimes ko rokte hain, banate huye ek safe aur khush environment.<br><br>" +
                "7. <b>Article 39(e) & (f):</b> Baccho ke liye \"<i>Sehat aur Suraksha ka Magic Shield</i>\" jisse unke swasthya aur Takat ka dhyan rakha jata hai, aur kishoro ki chmta ke anurup kaam ki nigrani ho sake. Iske alawa \"<i>Garima Rakshak</i>\" bhi, jo baccho ko Surakshit aur samman ke saath acche Vatavaran me rehne ka Adhikar deta hai.";

        // Use HtmlCompat.fromHtml for compatibility
        content.setText(fromHtml(cont, HtmlCompat.FROM_HTML_MODE_LEGACY));

        return view;

    }
}