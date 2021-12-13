package com.itheima.sunnyweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itheima.sunnyweather.MainActivity
import com.itheima.sunnyweather.R
import com.itheima.sunnyweather.databinding.FragmentPlaceBinding
import com.itheima.sunnyweather.ui.weather.WeatherActivity


class PlaceFragment:Fragment() {


//    private var _binding: FragmentPlaceBinding? = null
//
//    private val binding get() = _binding!!

    val viewModel by lazy { ViewModelProvider(this).get((PlaceViewModel::class.java)) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //如果存储了地址值 发起请求并跳转页面
        if(activity is MainActivity && viewModel.isPlaceSaved()){
            val place = viewModel.getSavedPlace()
            val intent = Intent(context,WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        val layoutInflater = LinearLayoutManager(activity)
        val recyclerView = activity!!.findViewById<RecyclerView>(R.id.recyclerView)
        val searchPlaceEdit = activity!!.findViewById<EditText>(R.id.searchPlaceEdit)
        val bgImageView = activity!!.findViewById<ImageView>(R.id.bgImageView)

        recyclerView.layoutManager = layoutInflater
        adapter = PlaceAdapter(this,viewModel.placeList)
        recyclerView.adapter=adapter

        //当此编辑栏内容改变时
        searchPlaceEdit.addTextChangedListener { editable ->
            //获取编辑栏内容
            val content =editable.toString()
            //如果不为空
            if(content.isNotEmpty()){
                //传入需要查找的位置 并发送GET请求
                viewModel.searchPlaces(content)
            }else{
                //为空则隐藏recyclerview 显示背景图 通知adapter内容改变
                recyclerView.visibility=View.GONE
                bgImageView.visibility=View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        //LiveData数据变化的回调函数
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result->
            val places = result.getOrNull()
            if(places!=null){
                //数据不为空
                recyclerView.visibility=View.VISIBLE
                bgImageView.visibility =View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                //数据为空
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}