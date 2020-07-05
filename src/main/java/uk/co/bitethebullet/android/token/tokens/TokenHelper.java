package uk.co.bitethebullet.android.token.tokens;

import android.database.Cursor;

import java.util.ArrayList;

import uk.co.bitethebullet.android.token.datalayer.TokenDbAdapter;


public class TokenHelper {

    TokenDbAdapter mDbAdaptor;

    public TokenHelper(TokenDbAdapter dbAdapter){
        this.mDbAdaptor = dbAdapter;
    }

    public ArrayList<IToken> getTokens(){
        ArrayList<IToken> tokens = new ArrayList<IToken>();
        Cursor c = mDbAdaptor.fetchAllTokens();

        if (c.moveToFirst()){
            do{
                tokens.add(TokenFactory.CreateToken(c));
            }while(c.moveToNext());
        }
        c.close();

        return tokens;
    }

    public CharSequence[] getTokenFullNames(){
        ArrayList<IToken> tokens = this.getTokens();

        CharSequence[] charSequences = new CharSequence[tokens.size()];
        for(int i = 0; i < tokens.size(); i++){
            StringBuilder sb = new StringBuilder();

            if(tokens.get(i).getOrganisation() != null){
                sb.append(tokens.get(i).getOrganisation());
                sb.append("/");
            }

            sb.append(tokens.get(i).getName());

            charSequences[i] = sb.toString();
        }

        return charSequences;
    }

}
