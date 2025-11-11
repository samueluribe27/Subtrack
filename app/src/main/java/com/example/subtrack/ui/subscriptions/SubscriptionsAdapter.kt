package com.example.subtrack.ui.subscriptions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.subtrack.R
import com.example.subtrack.ui.view.Subscription
import com.example.subtrack.ui.view.ViewActivity
import java.text.DecimalFormat

/**
 * Adaptador para mostrar la lista de suscripciones en un RecyclerView.
 * @param subscriptions La lista mutable de suscripciones activas.
 * @param onDeleteClicked Un callback que se ejecuta cuando se hace clic en el botón de eliminar.
 * Pasa la ID de la suscripción a la Activity/Fragment para que maneje la eliminación.
 */
class SubscriptionAdapter(
    private var subscriptions: MutableList<Subscription>,
    private val onDeleteClicked: (subscriptionId: String) -> Unit
) : RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Asegúrate de que estas IDs existen en R.layout.item_subscription.xml
        val tvInitial: TextView = view.findViewById(R.id.tvInitial)
        val tvSubscriptionName: TextView = view.findViewById(R.id.tvSubscriptionName)
        val tvPaymentDate: TextView = view.findViewById(R.id.tvPaymentDate)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        // El botón de eliminar (ImageView)
        val btnDeleteSubscription: ImageView = view.findViewById(R.id.btnDeleteSubscription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla el layout de la tarjeta individual (item_subscription.xml)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subscription, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subscription = subscriptions[position]

        // Formato para mostrar el precio en moneda
        val currencyFormatter = DecimalFormat("$#,##0.00")

        // Rellenar las vistas con los datos de la suscripción
        holder.tvInitial.text = subscription.name.firstOrNull()?.toString() ?: ""
        holder.tvSubscriptionName.text = subscription.name
        holder.tvPaymentDate.text = subscription.nextPaymentDate
        holder.tvPrice.text = currencyFormatter.format(subscription.price)

        // Configurar el listener del botón de eliminar
        holder.btnDeleteSubscription.setOnClickListener {
            // Llama al callback en la Activity/Fragment
            onDeleteClicked(subscription.id)
        }
    }

    override fun getItemCount(): Int = subscriptions.size

    /**
     * Notifica al RecyclerView que un elemento fue eliminado en una posición específica.
     * Esto permite la animación de eliminación.
     */
    fun notifyDataRemoved(position: Int) {
        notifyItemRemoved(position)
    }
}