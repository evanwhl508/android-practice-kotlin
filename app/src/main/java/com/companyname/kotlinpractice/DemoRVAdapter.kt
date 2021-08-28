package com.companyname.kotlinpractice;

import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.companyname.kotlinpractice.databinding.LayoutListItemBinding
import com.companyname.kotlinpractice.firestore.FirestoreManager
import com.companyname.kotlinpractice.room.RoomFavCoin
import com.companyname.kotlinpractice.room.RoomManager
import com.google.firebase.firestore.SetOptions
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.List


class DemoRVAdapter : RecyclerView.Adapter<DemoRVAdapter.ViewHolder>() {
    private var coins: List<Coin> = Collections.emptyList()

    fun setCoins(coins: List<Coin>) {
        this.coins = coins
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.getContext());
        val binding: LayoutListItemBinding = LayoutListItemBinding.inflate(layoutInflater, parent, false);
        return ViewHolder(binding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin: Coin = coins.get(position);
        holder.bind(coin);
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    class ViewHolder(var binding: LayoutListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var coin:Coin;

        fun bind(c: Coin) {
            binding.coinEntity = c;
            coin = c;
        }

        init {
            this.binding.root.setOnClickListener{
                CoinDetailActivity.start(it, coin, binding.tvName, binding.ivIcon)
            }

            this.binding.checkBoxFav.setOnClickListener{
                val c = RoomFavCoin().apply {
                    coinId = coin.id
                }
                RoomManager.instance.db.favCoinDao()?.let{
                    if (this.binding.checkBoxFav.isChecked) {
                        it.insertFavCoin(c).subscribeOn(AndroidSchedulers.mainThread()).subscribe{
                            this.binding.checkBoxFav.setButtonDrawable(R.drawable.ic_baseline_star_24)
                        }
                    } else {
                        it.delete(c).subscribeOn(AndroidSchedulers.mainThread()).subscribe{
                            this.binding.checkBoxFav.setButtonDrawable(R.drawable.ic_baseline_star_border_24)
                        }
                    }
                    it.getAllFavCoin().subscribe{ res -> Log.e("Room", ": $res")}

                    }
            }
            this.binding.checkBoxAlert.setOnClickListener{
//                Log.e("alert", "clicked!")
                val builder: AlertDialog.Builder = AlertDialog.Builder(binding.root.context)
                builder.setTitle("Alert Price")
// Set up the input
                val input = EditText(binding.root.context)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.inputType = InputType.TYPE_CLASS_NUMBER
                builder.setView(input)
// Set up the buttons
                builder.setPositiveButton("Confirm"
                ) { dialog, which ->
//                    Log.e("Confirm Button", coin.id + ": " + input.text.toString())

                    val pref =
                        binding.root.context.getSharedPreferences("firebase", Context.MODE_PRIVATE)
                    val username = pref.getString("user", null)
//                    Log.e("Confirm Button", "$username")
                    username?.let {
                        val timestamp = Timestamp(System.currentTimeMillis()).time
                        FirestoreManager.instance.db
                            .document("users/$username/price_alert/${coin.id}_$timestamp")
                            .set(HashMap<String, Any>().apply {
                                put("timestamp", timestamp)
                                put("direction", "upper")
                                put("price", input.text.toString())
                                put("id", coin.id)
                            }, SetOptions.merge())
                    }
                }
                builder.setNegativeButton("Cancel"
                ) { dialog, which -> dialog.cancel() }
                builder.show()
            }
        }

//        public ViewHolder(@NonNull LayoutListItemBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//
//            this.binding.getRoot().setOnClickListener(v -> {
//                if (coin != null) {
//                    CoinDetailActivity.start(v, coin, binding.tvName, binding.ivIcon);
//                }
//            });
//
//            this.binding.checkBoxFav.setOnClickListener(v -> {
//                RoomFavCoin c = new RoomFavCoin();
//                c.coinId=this.coin.getId();
//                if (this.binding.checkBoxFav.isChecked()) {
//                    if (RoomManager.instance.db != null) {
//                        if (RoomManager.instance.db.favCoinDao() != null) {
//                            RoomManager.instance.db.favCoinDao().insertFavCoin(c);
//                        }
//                    }
//                }
//            });
////            binding.executePendingBindings();
//        }
    }
}
